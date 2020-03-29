FROM mcmoe/mssqldocker:latest

COPY ./create_db.sh /init.sh

RUN chmod +x /init.sh

RUN /init.sh