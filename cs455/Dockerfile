FROM golang:latest

COPY Makefile Makefile
RUN make redis

COPY src src
RUN make id-server
RUN make id-client

EXPOSE 5185/tcp
EXPOSE 5187/tcp
EXPOSE 5189/udp

ENV PATH=.:${PATH}

CMD ["./server", "-verbose"]
