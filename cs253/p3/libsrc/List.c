#include "List.h"
#include <stdio.h>
#include <stdlib.h>

struct list *createList(int (*equals)(const void *, const void *),
                        char *(*toString)(const void *),
                        void (*freeObject)(void *)) {
  struct list *list;
  list = (struct list *)malloc(sizeof(struct list));
  list->size = 0;
  list->head = NULL;
  list->tail = NULL;
  list->equals = equals;
  list->toString = toString;
  list->freeObject = freeObject;
  return list;
}

void freeList(struct list *list) {
  if (list == NULL){
    return;
  } else {
  while (getSize(list) > 0) {
    freeNode(removeFront(list), list->freeObject);
  }
  free(list);
  }
}

int getSize(const struct list *list) { return list->size; }

int isEmpty(const struct list *list) { return list->size == 0; }

void addAtFront(struct list *list, struct node *node) {
  if (list == NULL)
    return;
  if (node == NULL)
    return;
  list->size++;
  node->next = list->head;
  node->prev = NULL;
  if (list->head == NULL) {
    list->head = node;
    list->tail = node;
  } else {
    list->head->prev = node;
    list->head = node;
  }
}

void addAtRear(struct list *list, struct node *node) {
  if (list == NULL)
    return;
  if (node == NULL)
    return;
  list->size++;
  node->prev = list->tail;
  node->next = NULL;
  if (list->head == NULL) {
    list->head = node;
    list->tail = node;
  } else {
    list->tail->next = node;
    list->tail = node;
  }
}

struct node *removeFront(struct list *list) {
  if (list == NULL || list->head == NULL) {
    return NULL;
  } else {
    struct node *retNode;
    retNode = list->head;
    struct node *newHead = retNode->next;
    list->head = newHead;
    if (newHead != NULL) {
      newHead->prev = NULL;
    }
    retNode->next = NULL;
    list->size--;
    if (list->size == 0) {
      list->head = NULL;
      list->tail = NULL;
    }
    return retNode;
  }
}

struct node *removeRear(struct list *list) {
  if (list == NULL || list->head == NULL) {
    return NULL;
  } else {
    struct node *retNode;
    retNode = list->tail;
    struct node *newTail = retNode->prev;
    list->tail = newTail;
    if (newTail != NULL) {
      newTail->next = NULL;
    }
    retNode->prev = NULL;
    list->size--;
    if (list->size == 0) {
      list->head = NULL;
      list->tail = NULL;
    }
    return retNode;
  }
}

struct node *removeNode(struct list *list, struct node *node) {
  if (list == NULL || node == NULL) {
    return NULL;
  } else {
    if (node == list->head) {
      return removeFront(list);
    } else if (node == list->tail) {
      return removeRear(list);
    } else {
      node->next->prev = node->prev;
      node->prev->next = node->next;
      node->next = NULL;
      node->prev = NULL;
      list->size--;
      return node;
    }
  }
}

struct node *search(const struct list *list, const void *obj) {
  if (list == NULL || obj == NULL || list->head == NULL) {
    return NULL;
  } else {
    struct node *found = list->head;
    for (;found != NULL;) {
      if (list->equals(found->obj, obj)) {
        return found;
      } else {
        found = found->next;
      }
    }
    return NULL;
  }
}

void reverseList(struct list *list) {
  if (list != NULL) {
    struct node *currentNode = list->tail;
    list->tail = list->head;
    list->head = currentNode;
    while (currentNode != NULL) {
      struct node *tmpNode = currentNode->prev;
      currentNode->prev = currentNode->next;
      currentNode->next = tmpNode;
      currentNode = currentNode->next;
    }
  }
}

void printList(const struct list *list) {
  if (!list)
    return; // list was null!!
  char *output;
  struct node *temp = list->head;
  while (temp) {
    output = list->toString(temp->obj);
    printf("%s\n", output);
    free(output);
    temp = temp->next;
  }
}
