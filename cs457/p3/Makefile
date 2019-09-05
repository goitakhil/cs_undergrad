CC = gcc
TARGET = prover
OBJ =  prover.o
LIBS = 
INC = -I./ 
DEF = 
CFLAGS = $(INC) $(DEF) -ggdb -Og

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) $(OBJ) -o $(TARGET) $(LIBS)

clean:	
	rm -f *.o prover

