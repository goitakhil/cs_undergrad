
#include <jacobi2d.h>
#include <gtest/gtest.h>

TEST (NormalTests, Zeros){

    double data[4 * 4] = {0,0,0,0,
                         0,0,0,0,
                         0,0,0,0,
                         0,0,0,0};
    double* pData = &data[0];

    double* output = jacobi2d(pData, 4, 4, 1);

    ASSERT_FALSE(output == NULL);
    double expected[4 * 4] = {0,0,0,0,
                             0,0,0,0,
                             0,0,0,0,
                             0,0,0,0};
    for (int i = 0; i < (4 * 4); i++){
        EXPECT_DOUBLE_EQ(output[i], expected[i]);
    }
}


TEST (NormalTests, Box50){
    double data[4 * 4] = {1,1,1,1,
                         1,0,0,1,
                         1,0,0,1,
                         1,1,1,1};
    
    double* output = jacobi2d(data, 4, 4, 0.5);
    ASSERT_FALSE(output == NULL);
    double expected[4 * 4] = {1, 1, 1,1,
                             1,.5,.5,1,
                             1,.5,.5,1,
                             1, 1, 1,1};
    for (int i = 0; i < (4 * 4); i++){
        EXPECT_DOUBLE_EQ(output[i], expected[i]);
    }
}

TEST (NormalTests, Box25){
    double data[4 * 4] = {1,1,1,1,
                         1,0,0,1,
                         1,0,0,1,
                         1,1,1,1};
   
    double* output = jacobi2d(data, 4, 4, 0.25);
    ASSERT_FALSE(output == NULL);
    double expected[4 * 4] = {1,  1,  1,1,
                             1,.75,.75,1,
                             1,.75,.75,1,
                             1,  1,  1,1};
    for (int i = 0; i < (4 * 4); i++){
        EXPECT_DOUBLE_EQ(output[i], expected[i]);
    }
}

TEST (NormalTests, Example1){
    double data[4 * 4] = {4,4,4,4,
                         4,0,4,4,
                         4,4,0,4,
                         4,4,4,4};
    
    double* output = jacobi2d(data, 4, 4, 3);
    ASSERT_FALSE(output == NULL);
    double expected[4 * 4] = {4,4,4,4,
                             4,3,4,4,
                             4,4,3,4,
                             4,4,4,4};
    for (int i = 0; i < (4 * 4); i++){
        EXPECT_DOUBLE_EQ(output[i], expected[i]);
    }
}

TEST (NormalTests, Example2){
    double data[4 * 4] = {4,4,4,4,
                         4,0,4,4,
                         4,4,0,4,
                         4,4,4,4};
    
    double* output = jacobi2d(data, 4, 4, 1);
    ASSERT_FALSE(output == NULL);
    double expected[4 * 4] = {4,  4,  4,4,
                             4,  4,3.5,4,
                             4,3.5,  4,4,
                             4,  4,  4,4};
    for (int i = 0; i < (4 * 4); i++){
        EXPECT_DOUBLE_EQ(output[i], expected[i]);
    }
}

TEST (NormalTests, Example3){
    double data[5 * 5] = {4,4,4,4,4,
                         4,0,4,0,4,
                         4,4,0,4,4,
                         4,0,4,0,4,
                         4,4,4,4,4};
    
    double* output = jacobi2d(data, 5, 5, 1);
    ASSERT_FALSE(output == NULL);
    double expected[5 * 5] = {4,    4,    4,    4,  4,
                              4, 3.625,    4, 3.625,  4,
                              4,    4, 3.25,    4,  4,
                              4, 3.625,    4, 3.625,  4,
                              4,    4,    4,    4,  4};

    for (int i = 0; i < (5 * 5); i++){
        EXPECT_DOUBLE_EQ(output[i], expected[i]);
    }
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
