#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <time.h>
#include <sys/time.h>
#include <stdbool.h>

#define MAXPRED 50
#define MAXPARAM 10
#define MAXSENT 100000
#define MAXSUB 1000
#define MAXSTRLEN 200

int tryResolution(int sent1, int sent2);
void printSent(int sent);

double rTime, hTime;
int rSteps, hSteps;

int RefuteFlag=0;

typedef struct {
    char name[32];   /* Predicate name */
    int numparam;   /* Number of parameters the predicate requires */
} Predicate;

Predicate predlist[MAXPRED];

typedef struct {
    char con[16];   /* Storage for when the parameter is a constant */
    int var;   /* Storage for when the parameter is a variable */
} Parameter;

typedef struct {
    Parameter * var;
    Parameter * val;
} Assignment;

typedef struct {
    char comment[MAXSTRLEN]; /* comment from input file */
    int refutePart;          /* set to true if this sentence came from the negated part of the knowledge base */
    int pred[MAXPRED];         /* List of predicates in sentence (indexes into Predicate array) */
    int neg[MAXPRED];         /* Added by T. Andersen. neg[i] set to 1 if predicate indexed by pred[i] is negated */
    int num_pred;             /* Added by T. Andersen.  Stores the number of predicates for this sentence */
    Parameter param[MAXPRED][MAXPARAM];   /* List of parameters for each predicate */
    int parent1; /* a parent */
    int parent2; /* another parent */
} Sentence;

int unifyPred(int sent1, int p1, int sent2, int p2, Assignment *theta);

int sentptr;
Sentence sentlist[MAXSENT];
int nextvar;
int origin;

/* Returns true if the parameter is a constant */
int constant(Parameter param) {
    if(param.var <= 0 && param.con[0] != '\0') return 1; else return 0;
}

/* Returns true if the parameter is a variable */
int variable(Parameter param) {
    if(param.var > 0 && param.con[0] == '\0') return 1; else return 0;
}

/* Returns true if the parameter is empty */
int empty(Parameter *param) {
    if(param->var <= 0 && param->con[0] == '\0') return 1; else return 0;
}

/* Set the KB to empty */
void InitializeKB(void) {
    sentptr = 0;
    memset(sentlist,0,MAXSENT*sizeof(Sentence));
    memset(predlist,0,MAXPRED*sizeof(Predicate));
    nextvar = 1;
}   

/* Add a predicate to the predicate list */
int AddPredicate(char *name, int numparam) {
    int i;

    i = 0;
    /* Check to see if predicate already in list */
    while(predlist[i].name[0] != '\0' && strcmp(name,predlist[i].name)) i++;
    if(predlist[i].name[0] == '\0') {
        /* Not in predicate list, so add */
        strcpy(predlist[i].name,name); 
        predlist[i].numparam = numparam; 
    } 
    return i; 
} 

/* Standardize apart (Makes sure that each sentence has unique variables) */
void Standardize(char param[MAXPRED][MAXPARAM][16], Parameter sparam[MAXPRED][MAXPARAM], int pred[MAXPRED], int snum) { 
    int sub[256]; 

    for(int i=0; i<256; i++) sub[i] = -1; 
    for(int k=0; k<snum; k++) 
        for(int j=0; j<MAXPARAM; j++) {
            //i = pred[k];
            if(param[k][j][0] == '\0') continue;
            /*else if(isupper(param[k][j][0])) strcpy(sparam[i][j].con,param[k][j]);*/
            else if(isupper(param[k][j][0])) strcpy(sparam[k][j].con,param[k][j]);
            else {
                if(sub[(unsigned char) param[k][j][0]] == -1) {
                    sub[(unsigned char) param[k][j][0]] = nextvar;
                    sparam[k][j].var = nextvar;
                    nextvar++;
                }
                else {
                    sparam[k][j].var = sub[(unsigned char) param[k][j][0]];
                }
            }
        }
}

/* Add a sentence to the KB */
void AddSentence(int neg[MAXPRED],int pred[MAXPRED], char param[MAXPRED][MAXPARAM][16], int snum, char *leftover) {
    int i;
    for (int i = 0; i < sentptr; i++){
        if (!memcmp(&neg, &sentlist[i].neg, sizeof(int)*MAXPRED)){
            if (!memcmp(&pred, &sentlist[i].pred, sizeof(int)*MAXPRED)){
                return;
            }
        }
    }
    Standardize(param,sentlist[sentptr].param,pred,snum);
    for(i=0; i<snum; i++) {
        sentlist[sentptr].pred[i] = pred[i];
        sentlist[sentptr].neg[i] = neg[i];
    }
    if(*leftover == '.')
    {
        leftover++;
        leftover[strlen(leftover)-1]=0; /* get rid of new line char */
        strcpy(sentlist[sentptr].comment,leftover);
    }
    sentlist[sentptr].refutePart = RefuteFlag;
    sentlist[sentptr].num_pred = snum;
    sentlist[sentptr].parent1 = -1;
    sentlist[sentptr].parent2 = -1;
    sentptr++;
    printSent(sentptr-1);
}

/* Convert text version of a sentence into internal representation */
int StringToSentence(char *line) {
    char pname[32],param[MAXPRED][MAXPARAM][16];
    int neg[MAXPRED],pred[MAXPRED],snum;

    memset(param,0,MAXPRED*MAXPARAM*16*sizeof(char));
    int i = 0;
    snum = 0;
    while((line[i] != '\0') && (line[i] != '\n') && (line[i] != '.')){
        /* 'neg' will keep track of whether the predicate is negated or not */
        neg[snum]=0;
        while(isspace(line[i])) i++;
        if((line[i] == '\0') || (line[i] == '\n') || (line[i] == '.')) break;
        if(line[i] == '!') {
            neg[snum] = 1;
            i++;
            while(isspace(line[i])) i++; /* Added by Tim Andersen.  just in case someone puts space(s) 
                                            between the ! and the beginning of the predicate name */
        }
        /* get predicate name */
        int j = i;
        /* while(line[j] != '(' && line[j] != '\0') j++; commented out by Tim Andersen */
        /* The following line added by Tim Andersen to insure that a predicate name only includes text characters */
        while(((line[j] >= 'a') && (line[j] <= 'z')) || ((line[j]>='A') && (line[j]<='Z'))) j++;
        if(line[j] != '(') return 0;  /* we better see the parameter list immediately after the predicate name */
        if(j == i) /* added by Tim Andersen - we better have at least one char in name */
        {
            return 0;  
        }
        memset(pname,0,32*sizeof(char));
        strncpy(pname,&line[i],j-i);

        /* get the parameters */
        int done = 0;
        int p = 0;
        while(!done) {
            i = j+1;
            while(isspace(line[i])) i++;
            j = i;
            /* while(line[j] != ',' && line[j] != ')' && line[j] != '\0') j++; commented out by Tim Andersen */
            /* The following line added by Tim Andersen to insure that a parameter name only includes text characters */
            while(((line[j] >= 'a') && (line[j] <= 'z')) || ((line[j]>='A') && (line[j]<='Z'))) j++;
            switch(line[j]) {
                case ' ':       /* added by Tim Andersen to allow spaces here */
                case ')': 
                case ',': strncpy(param[snum][p],&line[i],j-i); p++; break;
                default: return 0;  
            }
            while(isspace(line[j])) j++;
            switch(line[j])
            {
                case ')': done =1;
                case ',':
                          break;
                default: return 0;
            }
        }
        i = j+1;
        pred[snum] = AddPredicate(pname,p);
        snum++;
    }
    AddSentence(neg,pred,param,snum,&line[i]);
    return 1;
}

/* Read in a KB from a text file */
int ReadKB(char *filename) {
    FILE *kbfile;
    char line[255];

    kbfile = fopen(filename,"rt");
    if(!kbfile) 
    {
        fprintf(stderr,"File %s not found.\n", filename);
        return 0;
    }
    while(fgets(line,255,kbfile) != 0) {
        if(line[0]=='\n') RefuteFlag=1;  /* the rest after the first blank line should be the negated conclusion */
        else if(!StringToSentence(line)) 
        {
            fprintf(stderr,"Unable to parse line %s\n",line);
            fclose(kbfile);
            return 0;
        }
    }
    fclose(kbfile);
    return 1;
}

/* Load a KB from a text file */
void LoadKB(void) {
    char filename[255];

    printf("\nEnter filename: ");
    fgets(filename,255,stdin);
    if(!ReadKB(filename)) InitializeKB();
}

/* Print the current KB to the screen */
void ShowKB(void) {
    int i,j,k,p;

    printf("\nCurrent Knowledge Base\n");
    for(i=0; i<sentptr; i++) 
    {
        printf("%d: ",i);
        for(j=0; j<sentlist[i].num_pred; j++) 
        {
            if(sentlist[i].neg[j]) printf("!");
            p = sentlist[i].pred[j];
            printf("%s(",predlist[p].name);
            for(k=0; k<predlist[p].numparam; k++) 
            {
                if(constant(sentlist[i].param[j][k])) printf("%s",sentlist[i].param[j][k].con);
                else printf("%c",'a'+(unsigned char) sentlist[i].param[j][k].var%26);
                if(k<predlist[p].numparam-1) printf(","); else printf(") ");
            }
        }
        if(strlen(sentlist[i].comment)) printf("  //%s",sentlist[i].comment);
        if(sentlist[i].refutePart) printf("  :from refuted part");
        printf("\n");
    }
    printf("\n");
}

/* Allow user to enter a sentence to be added to KB */
void AddKBSentence(void) {
    char sent[255];

    printf("\nEnter sentence: ");
    fgets(sent,255,stdin);
    StringToSentence(sent);
}

void printSent(int sent){
    if (sent < 0){
        return;
    }
    printf("%d: ",sent);
    for(int j=0; j<sentlist[sent].num_pred; j++) 
    {
        if(sentlist[sent].neg[j]) printf("!");
        int p = sentlist[sent].pred[j];
        printf("%s(",predlist[p].name);
        for(int k=0; k<predlist[p].numparam; k++) 
        {
            if(constant(sentlist[sent].param[j][k])) printf("%s",sentlist[sent].param[j][k].con);
            else printf("%c",'a'+(unsigned char) sentlist[sent].param[j][k].var%26);
            if(k<predlist[p].numparam-1) printf(","); else printf(") ");
        }
    }
    printf("\n");
}

void printResult(int goal){
    if (goal >= origin){
        int p1 = sentlist[goal].parent1;
        int p2 = sentlist[goal].parent2;
        if (sentlist[goal].num_pred > 0){
            printSent(goal);
            printf("FROM:\n");
        } else {
            printf("Contradiction Found:\n");
        }
        printSent(p1);
        printf("AND:\n");
        printSent(p2);
        if (sentlist[goal].num_pred == 0){
            printf("Intermediate Steps to Proof:\n\n");
        } else {
            printf("\n");
        }
        printResult(p1);
        printResult(p2);
    }
}

/* You must write this function */
void RandomResolve()
{
    rTime=0.0;
    struct timeval start;
    gettimeofday(&start, NULL);
    rSteps=0;

    while(true){
        int sent1 = random() % sentptr;
        int sent2 = random() % sentptr;
        if (sent1 == sent2){
            continue;
        }
        rSteps++;
        if (tryResolution(sent1, sent2)){
            struct timeval end;
            gettimeofday(&end, NULL);
            rTime = (double) (end.tv_usec - start.tv_usec) / 1000000 + (double) (end.tv_sec - start.tv_sec);
            return;
        }
    }

}

/* You must write this function */
void HeuristicResolve()
{
    hTime=0.0;
    struct timeval start;
    gettimeofday(&start, NULL);
    hSteps=0;

    for (int i = 0; i < origin; i++) 
    for (int j = 0; j < origin; j++) {
        if (i != j) {
            hSteps++;
            if (tryResolution(i, j)){
                struct timeval end;
                gettimeofday(&end, NULL);
                hTime = (double) (end.tv_usec - start.tv_usec) / 1000000 + (double) (end.tv_sec - start.tv_sec);
                return;
            }
        }
    }

    while(true){
        int sent1 = (random() % (sentptr - origin)) + origin;
        int sent2 = random() % origin;
        if (sent1 == sent2){
            continue;
        }
        hSteps++;
        if (tryResolution(sent1, sent2)){
            struct timeval end;
            gettimeofday(&end, NULL);
            hTime = (double) (end.tv_usec - start.tv_usec) / 1000000 + (double) (end.tv_sec - start.tv_sec);
            return;
        }
    }
}

void Resolve(void) {
    origin = sentptr;
    HeuristicResolve();
    sentptr = origin;
    RandomResolve();
    printResult(sentptr-1);
    printf("RandomResolve: #steps = %i, time = %lg\n",rSteps, rTime);
    printf("HeuristicResolve: #steps = %i, time = %lg\n",hSteps, hTime);
    printf("Heuristic vs Random ratios:  hSteps/rSteps = %lg, hTime/rTime = %lg\n\n",(double)hSteps/(double)rSteps, hTime/rTime);
}

int tryResolution(int sent1, int sent2){
    int p1,p2;
    Assignment theta[MAXPARAM];
    for (p1=0; p1<sentlist[sent1].num_pred ; p1++){
        for (p2=0; p2<sentlist[sent2].num_pred ; p2++){
            int assignments = unifyPred(sent1, p1, sent2, p2, theta);
            if (assignments > 0){
                int negs[MAXPRED];
                int preds[MAXPRED];
                Parameter params[MAXPRED][MAXPARAM];
                int numpred = 0;
                // apply changes to the two sentences. Use copies.
                Sentence s1;
                Sentence s2;
                memcpy(&s1, &sentlist[sent1], sizeof(Sentence));
                memcpy(&s2, &sentlist[sent2], sizeof(Sentence));

                for (int x = 0; x < assignments; x++){
                    for (int pred = 0; pred < s1.num_pred; pred++){
                        for (int param = 0; param < predlist[s1.pred[pred]].numparam; param++){
                            if (theta[x].var->var == s1.param[pred][param].var ){
                                s1.param[pred][param] = *(theta[x].val);
                            }
                        }
                    }
                    for (int pred = 0; pred < s2.num_pred; pred++){
                        for (int param = 0; param < predlist[s2.pred[pred]].numparam; param++){
                            if (theta[x].var->var == s2.param[pred][param].var ){
                                s2.param[pred][param] = *(theta[x].val);
                            }
                        }
                    }
                }
                for (int pred = 0; pred < s1.num_pred; pred++){
                    bool duplicate = false;
                    for (int pred2 = 0; pred2 < s2.num_pred; pred2++){
                        if (s1.pred[pred] == s2.pred[pred2]){
                                duplicate = true;
                        }
                    }
                    if (duplicate) continue;
                    negs[numpred] = s1.neg[pred];
                    preds[numpred] = s1.pred[pred];
                    memcpy(params[numpred], s1.param[pred], sizeof(Parameter)*MAXPARAM);
                    numpred++;
                }
                for (int pred = 0; pred < s2.num_pred; pred++){
                    bool duplicate = false;
                    for (int pred2 = 0; pred2 < s1.num_pred; pred2++){
                        if (s2.pred[pred] == s1.pred[pred2]) {
                                duplicate = true;
                        }
                    }
                    for (int pred2 = 0; pred2 < numpred; pred2++){
                        if (s2.pred[pred] == preds[pred2]){
                                duplicate = true;
                        }
                    }
                    if (duplicate) continue;
                    negs[numpred] = s2.neg[pred];
                    preds[numpred] = s2.pred[pred];
                    memcpy(params[numpred], s2.param[pred], sizeof(Parameter)*MAXPARAM);
                    numpred++;
                }
                // Check if sentence alread in kb
                bool duplicate = false;
                for (int i = 0; i < sentptr; i++){
                    if (!memcmp(negs, sentlist[i].neg, sizeof(int)*MAXPRED)){
                        if (!memcmp(preds, sentlist[i].pred, sizeof(int)*MAXPRED)){
                            duplicate = true;
                        }
                    }
                }
                if (duplicate && numpred > 0) continue;
                // Insert at end of sent list
                for(int i = 0; i<numpred; i++) {
                    sentlist[sentptr].pred[i] = preds[i];
                    sentlist[sentptr].neg[i] = negs[i];
                    memcpy(sentlist[sentptr].param[i], params[i], sizeof(Parameter)*MAXPARAM);
                }
                sentlist[sentptr].num_pred = numpred;
                sentlist[sentptr].parent1 = sent1;
                sentlist[sentptr].parent2 = sent2;
                sentptr++;
                if (numpred == 0){
                    return true;
                }
                // DEBUG
                //printSent(sentptr-1);
            }
        }   
    }
    return false;
}

int unifyPred(int sent1, int p1, int sent2, int p2, Assignment *theta){
    if(sentlist[sent1].neg[p1] == sentlist[sent2].neg[p2]) return -1;
    if (sentlist[sent1].pred[p1] != sentlist[sent2].pred[p2]) return -1;

    Parameter param1[MAXPARAM];
    Parameter param2[MAXPARAM];
    memcpy(param1, sentlist[sent1].param[p1], sizeof(Parameter)*MAXPARAM);
    memcpy(param2, sentlist[sent2].param[p2], sizeof(Parameter)*MAXPARAM);

    int assignments = 0;
    int param;
    for (param=0; param<predlist[sentlist[sent1].pred[p1]].numparam; param++)
    {

        // Make changes from previous assignments
        int x;
        for(x=0; x<assignments; x++)
        {
            if (!memcmp(&param1[param], theta[x].var, sizeof(Parameter)))
            {
                param1[param] = *(theta[x].val);
            }
            if (!memcmp(&param2[param], theta[x].var, sizeof(Parameter)))
            {
                param2[param] = *(theta[x].val);
            }
        }

        // This is the actual unify step
        if (memcmp(&param1[param], &param2[param], sizeof(Parameter)))
        {
            if(variable(param1[param]))
            {
                theta[assignments].var = &(param1[param]);
                theta[assignments].val = &(param2[param]);
                assignments++;
            } else if (variable(param2[param]))
            {
                theta[assignments].var = &(param2[param]);
                theta[assignments].val = &(param1[param]);
                assignments++;
            } else 
            {
                return -1;
            }
        }
    }
    return assignments;
}

/* User enters a the negation of their query.  This is added to KB, and then KB is resolved to find solution */
void ProveQuery(void) {
    char query[255];

    printf("\nEnter negated query: ");
    fgets(query,255,stdin);
    StringToSentence(query);
    Resolve();
}

int main(int argc, char *argv[])
{
    char choice[64];
    int done;

    srand((unsigned int) time(0));
    if(argc > 2) {
        printf("Usage: prover [filename]\n");
        exit(0);
    }
    InitializeKB();
    if(argc == 2) {
        char * filename = argv[1];
        if(!ReadKB(filename)) 
        {
            printf("Syntax error in knowledge base\n");
            exit(0);
        }
        ShowKB();
        Resolve();
        exit(0);
    }
    done = 0;
    while(!done) {   
        do {
            system("clear");
            printf("\nMain Menu");
            printf("\n---------");
            printf("\n(A)dd sentence to database");
            printf("\n(C)lear database");
            printf("\n(L)oad database");
            printf("\n(S)how database");
            printf("\n(P)rove query");
            printf("\n(Q)uit program");
            printf("\n\nChoice: ");
            fgets(choice,64,stdin);
        } 
        while(choice[0] != 'a' && choice[0] != 'c' && choice[0] != 'l' && 
                choice[0] != 'p' && choice[0] != 's' && choice[0] != 'q');
        switch(choice[0]) {
            case 'a': AddKBSentence(); break;
            case 'c': InitializeKB(); break;
            case 'l': InitializeKB(); LoadKB(); break;
            case 's': {
                          char temp[100];
                          ShowKB(); 
                          printf("Press enter to continue... ");
                          fgets(temp,100,stdin);
                          break;
                      }
            case 'p': ProveQuery(); break;
            case 'q': done = 1; break;
        }
    }
    return 0;
}

