#include <gtest/gtest.h>
#include "../include/mvmultiply.hpp"

// test a 2x2 matrix with a 2 element vector.
TEST (TestMVMult, 2x2Matrix_2Vector) {

    //initialize test matrix and vector
    double matrix[] = {1.0, 1.0,
                       1.0, 1.0};

    double vector[] = {1.0, 1.0};

    //Initialize expected output and result of mvMultiply()
    double expected[] = {2.0, 2.0};
    double* result = mvMultiply(matrix, vector, 2, 2, 2);

    //Fail test if result is NULL
    if (result == NULL) {
        FAIL() << "Result of mvMultiply() returns NULL";

    } else {

        //Compare the elements of expected and result.
        for(int i = 0; i < 2; i++) {
            EXPECT_DOUBLE_EQ(expected[i], result[i]);
        }
    }
}

// test a 3x3 matrix with a 3 element vector
TEST (TestMVMult, 3x3Matrix_3Vector) {

    double matrix[] = { 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0 };

    double vector[] = { 1.0, 1.0, 1.0 };

    double expected[] = {3.0, 3.0, 3.0};
    double* result = mvMultiply(matrix, vector, 3, 3, 3);

    //Fail test if result is NULL
    if (result == NULL) {
        FAIL() << "Result of mvMultiply() returns NULL";

    } else {

        //Compare the elements of expected and result.
        for(int i = 0; i < 3; i++) {
            EXPECT_DOUBLE_EQ(expected[i], result[i]);
        }  
    }
}

// Test a 4x4 matrix with a 4 element vector
TEST (TestMVMult, 4x4Matrix_4Vector) {

    double matrix[] = { 1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0, };

    double vector[] = { 1.0, 1.0, 1.0, 1.0 };

    double expected[] = {4.0, 4.0, 4.0, 4.0};
    double* result = mvMultiply(matrix, vector, 4, 4, 4);

    //Fail test if result is NULL
    if (result == NULL) {
        FAIL() << "Result of mvMultiply() returns NULL";

    } else {

        //Compare the elements of expected and result.
        for(int i = 0; i < 3; i++) {
            EXPECT_DOUBLE_EQ(expected[i], result[i]);
        }  
    }
}

// Test a 4x4 matrix with a 3 element vector. Should return an error value (-1.0)
TEST (TestMVMult, 4x4Matrix_3Vector) {

    double matrix[] = { 1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0, };

    double vector[] = { 1.0, 1.0, 1.0 };

    double expected = -1.0;
    double* result = mvMultiply(matrix, vector, 4, 4, 3);

    //Fail test if result is NULL
    if (result == NULL) {
        FAIL() << "Result of mvMultiply() returns NULL";

    } else {
        EXPECT_DOUBLE_EQ(expected, result[0]);
    }
}




int main (int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
