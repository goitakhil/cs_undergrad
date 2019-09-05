#include <gtest/gtest.h>

long double calculate_pi(unsigned long long iterations, int precision, int nthreads);
const long double ABS_ERROR = 0.00000000001;

TEST(CalculatePiTest, HandlesInvalidInput) {
	EXPECT_EQ(calculate_pi(-1,-1, 1), 0);
	EXPECT_EQ(calculate_pi(-1,-1, 4), 0);
}

TEST(CalculatePiTest, KnownPiComparison) {
	EXPECT_NEAR(calculate_pi(0, 5, 1), 3.14159, 0.00001);
	EXPECT_NEAR(calculate_pi(0, 5, 4), 3.14159, 0.00001);
}

TEST(CalculatePiTest, IterationTest1) {
	EXPECT_NEAR(calculate_pi(2, 0, 1), 4, ABS_ERROR);
	EXPECT_NEAR(calculate_pi(2, 0, 4), 4, ABS_ERROR);
}

TEST(CalculatePiTest, IterationTest2) {
	EXPECT_NEAR(calculate_pi(3, 0, 1), 2.66666666666, ABS_ERROR);
	EXPECT_NEAR(calculate_pi(3, 0, 4), 2.66666666666, ABS_ERROR);
}
