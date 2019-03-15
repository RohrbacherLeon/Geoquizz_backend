FROM mysql
COPY data.sql /docker-entrypoint-initdb.d/
EXPOSE 3306