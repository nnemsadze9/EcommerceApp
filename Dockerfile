FROM openjdk:11

RUN mkdir /app

COPY out/production/EcommerceApp/ /app

WORKDIR /app

CMD java Main