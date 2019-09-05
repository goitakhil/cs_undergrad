/*
 * UnitTestList.c
 *
 *      Author: marissa
 */

#include <stdio.h>
#include <stdlib.h>

#define TRUE 1
#define FALSE 0


#include "Object.h"
#include "Node.h"
#include "List.h"

/*
 * macro to mimic the functionality of assert() from <assert.h>. The difference is that this version doesn't exit the program entirely.
 * It will just break out of the current function (or test case in this context).
 */
#define myassert(expr) if(!(expr)) { fprintf(stderr, "\t[assertion failed] %s: %s\n", __PRETTY_FUNCTION__, __STRING(expr)); return FALSE; }

struct list *testlist;

int testCount = 0;
int passCount = 0;

void printTestInfo(char* testName, char *info)
{
	fprintf(stdout, "%s - %s\n", testName, info);
}

void printTestResult(char* testName, int passed)
{
	if(passed) {
		fprintf(stdout, "%s - %s\n\n", "[PASSED]", testName);
	} else {
		fprintf(stdout, "%s - %s\n\n", "[FAILED]", testName);
	}
}

struct node *createTestNode(int jobid)
{
	struct object * job = createObject(jobid, "cmd args");
	struct node *node = createNode(job);
	return node;
}

void addThreeNodes(){
	addAtRear(testlist, createTestNode(1));
	addAtRear(testlist, createTestNode(2));
	addAtRear(testlist, createTestNode(3));
}

int addAtFrontWithNoNodes()
{
	myassert(isEmpty(testlist) == true)
    struct node *node = createTestNode(1);
	addAtFront(testlist, node);
	myassert(testlist->size == 1)
    myassert(getSize(testlist) == 1)
    myassert(isEmpty(testlist) == false)
	myassert(testlist->head == node)
	myassert(testlist->tail == node)
	myassert(testlist->head->next == NULL)
	myassert(testlist->head->prev == NULL)
	return TRUE;
}

int addAtFrontWithOneNode()
{
	struct node *nodeA = createTestNode(1);
	addAtFront(testlist, nodeA);
	struct node *nodeB = createTestNode(2);
	addAtFront(testlist, nodeB);
	myassert(testlist->size == 2)
	myassert(testlist->head == nodeB)
	myassert(testlist->tail == nodeA)
	myassert(testlist->head->next == nodeA)
	myassert(testlist->head->prev == NULL)
	return TRUE;
}

int addAtFrontWithThreeNodes(){
	addThreeNodes();
	struct node *node = createTestNode(5);
	addAtFront(testlist, node);
	myassert(testlist->size == 4)
	myassert(testlist->head == node)
	return TRUE;
}

int addAtRearWithNoNodes()
{
	struct node *node = createTestNode(1);
	addAtRear(testlist, node);
	myassert(testlist->size == 1)
	myassert(testlist->head == node)
	myassert(testlist->tail == node)
	myassert(testlist->head->next == NULL)
	myassert(testlist->head->prev == NULL)
	return TRUE;
}

int addAtRearWithOneNode()
{
	struct node *nodeA = createTestNode(1);
	addAtFront(testlist, nodeA);
	struct node *nodeB = createTestNode(2);
	addAtRear(testlist, nodeB);
	myassert(testlist->size == 2)
	myassert(testlist->head == nodeA)
	myassert(testlist->tail == nodeB)
	myassert(testlist->head->next == nodeB)
	myassert(testlist->head->prev == NULL)
	myassert(testlist->tail->next == NULL)
	myassert(testlist->tail->prev == nodeA)
	return TRUE;
}

int addAtRearWithThreeNodes(){
	addThreeNodes();
	struct node *node = createTestNode(5);
	addAtRear(testlist, node);
	myassert(testlist->size == 4)
	myassert(testlist->tail == node)
	return TRUE;
}

int removeFromListWithOneNode()
{
	struct node *node = createTestNode(1);
	addAtFront(testlist, node);
	freeNode(removeNode(testlist, node), testlist->freeObject);
	myassert(testlist->size == 0)
	myassert(testlist->head == NULL)
	myassert(testlist->tail == NULL)
	return TRUE;
}

int removeFromListWithThreeNodes(){
	struct node *node1 = createTestNode(1);
	addAtRear(testlist, node1);
	struct node *node2 = createTestNode(2);
	addAtRear(testlist, node2);
	struct node *node3 = createTestNode(3);
	addAtRear(testlist, node3);
	myassert(removeNode(testlist, node2) == node2);
	freeNode(node2, testlist->freeObject);
	myassert(testlist->size == 2)
	myassert(testlist->head == node1)
	myassert(testlist->tail == node3)
	return TRUE;
}

int nullNodeTest()
{
	addAtFront(testlist, NULL);
	freeNode(NULL, testlist->freeObject);
	addAtRear(testlist, NULL);
	removeNode(testlist, NULL);
	search(testlist, NULL);
	addAtFront(NULL, NULL);
	freeNode(NULL, NULL);
	addAtRear(NULL, NULL);
	removeFront(NULL);
	removeRear(NULL);
	removeNode(NULL, NULL);
	search(NULL, NULL);
	reverseList(NULL);
	freeList(NULL);
	return TRUE;
}

 int reverseListTest()
{
    struct node *node1 = createTestNode(1);
	addAtFront(testlist, node1);
    struct node *node2 = createTestNode(2);
    addAtRear(testlist, node2);
    struct node *node3 = createTestNode(3);
	addAtRear(testlist, node3);
    myassert(testlist->size == 3)
    myassert(getSize(testlist) == 3)
    myassert(isEmpty(testlist) == false)
	myassert(testlist->head == node1)
	myassert(testlist->tail == node3)
	myassert(testlist->head->next == node2)
    myassert(testlist->tail->prev == node2)
    reverseList(testlist);
    myassert(testlist->head == node3)
	myassert(testlist->tail == node1)
	myassert(testlist->head->next == node2)
    myassert(testlist->tail->prev == node2)
	return TRUE;
}

int removeFrontOneNode(){
	struct node *node = createTestNode(1);
	addAtFront(testlist, node);
	myassert(removeFront(testlist) == node);
	freeNode(node, testlist->freeObject);
	myassert(testlist->size == 0)
	myassert(testlist->head == NULL)
	myassert(testlist->tail == NULL)
	return TRUE;
}

int removeFrontThreeNodes(){
	struct node *node1 = createTestNode(1);
	addAtRear(testlist, node1);
	struct node *node2 = createTestNode(2);
	addAtRear(testlist, node2);
	struct node *node3 = createTestNode(3);
	addAtRear(testlist, node3);
	myassert(removeFront(testlist) == node1);
	freeNode(node1, testlist->freeObject);
	myassert(testlist->size == 2)
	myassert(testlist->head == node2)
	myassert(testlist->tail == node3)
	return TRUE;
}

int removeRearOneNode(){
	struct node *node = createTestNode(1);
	addAtFront(testlist, node);
	myassert(removeRear(testlist) == node);
	freeNode(node, testlist->freeObject);
	myassert(testlist->size == 0)
	myassert(testlist->head == NULL)
	myassert(testlist->tail == NULL)
	return TRUE;
}

int removeRearThreeNodes(){
	struct node *node1 = createTestNode(1);
	addAtRear(testlist, node1);
	struct node *node2 = createTestNode(2);
	addAtRear(testlist, node2);
	struct node *node3 = createTestNode(3);
	addAtRear(testlist, node3);
	myassert(removeRear(testlist) == node3);
	freeNode(node3, testlist->freeObject);
	myassert(testlist->size == 2)
	myassert(testlist->head == node1)
	myassert(testlist->tail == node2)
	return TRUE;
}

int searchWithEmptyList(){
	struct node *node1 = createTestNode(1);
	myassert(search(testlist, node1->obj) == NULL)
	return TRUE;
}

int searchWithOneNode(){
	struct node *node1 = createTestNode(1);
	addAtRear(testlist, node1);
	myassert(search(testlist, node1->obj) == node1)
	return TRUE;
}

int searchWithThreeNodes(){
	struct node *node1 = createTestNode(1);
	addAtRear(testlist, node1);
	struct node *node2 = createTestNode(2);
	addAtRear(testlist, node2);
	struct node *node3 = createTestNode(3);
	addAtRear(testlist, node3);
	myassert(search(testlist, node1->obj) == node1)
	myassert(search(testlist, node2->obj) == node2)
	myassert(search(testlist, node3->obj) == node3)
	return TRUE;
}

void beforeTest(char* testName)
{
	printTestInfo(testName, "Running...");
	testlist = createList(equals, toString, freeObject);
	testCount++;
}
void afterTest(char* testName, int result)
{
	printTestResult(testName, result);
	freeList(testlist);
	passCount += result;
}


void runUnitTests()
{
	int result;
	char *testName;

	testName = "addAtFrontWithNoNodes";
	beforeTest(testName);
	result = addAtFrontWithNoNodes();
	afterTest(testName, result);

	testName = "addAtFrontWithOneNode";
	beforeTest(testName);
	result = addAtFrontWithOneNode();
	afterTest(testName, result);

	testName = "addAtFrontWithThreeNodes";
	beforeTest(testName);
	result = addAtFrontWithThreeNodes();
	afterTest(testName, result);

	testName = "addAtRearWithNoNodes";
	beforeTest(testName);
	result = addAtRearWithNoNodes();
	afterTest(testName, result);

	testName = "addAtRearWithOneNode";
	beforeTest(testName);
	result = addAtRearWithOneNode();
	afterTest(testName, result);

	testName = "addAtRearWithThreeNodes";
	beforeTest(testName);
	result = addAtRearWithThreeNodes();
	afterTest(testName, result);

	testName = "removeFromListWithOneNode";
	beforeTest(testName);
	result = removeFromListWithOneNode();
	afterTest(testName, result);

	testName = "removeFromListWithThreeNodes";
	beforeTest(testName);
	result = removeFromListWithThreeNodes();
	afterTest(testName, result);

	testName = "nullNodeTest";
	beforeTest(testName);
	result = nullNodeTest();
	afterTest(testName, result);
	
	testName = "reverseListTest";
	beforeTest(testName);
	result = reverseListTest();
	afterTest(testName, result);

	testName = "removeFrontOneNode";
	beforeTest(testName);
	result = removeFrontOneNode();
	afterTest(testName, result);

	testName = "removeFrontThreeNodes";
	beforeTest(testName);
	result = removeFrontThreeNodes();
	afterTest(testName, result);

	testName = "removeRearOneNode";
	beforeTest(testName);
	result = removeRearOneNode();
	afterTest(testName, result);

	testName = "removeRearThreeNodes";
	beforeTest(testName);
	result = removeRearThreeNodes();
	afterTest(testName, result);

	testName = "searchWithEmptyList";
	beforeTest(testName);
	result = searchWithEmptyList();
	afterTest(testName, result);

	testName = "searchWithOneNode";
	beforeTest(testName);
	result = searchWithOneNode();
	afterTest(testName, result);

	testName = "searchWithThreeNodes";
	beforeTest(testName);
	result = searchWithThreeNodes();
	afterTest(testName, result);

	
	fprintf(stdout, "Test Cases: %d\n",  testCount);
	fprintf(stdout, "Passed: %d\n", passCount);
	fprintf(stdout, "Failed: %d\n", testCount - passCount);
}

int main(int argc, char *argv[])
{
	runUnitTests();
	exit(0);
}
