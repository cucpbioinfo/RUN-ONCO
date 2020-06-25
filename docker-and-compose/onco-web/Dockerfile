### STAGE 1: Build ###

# We label our stage as 'builder'
FROM node:10-alpine as builder

# Set the working directory to /app
WORKDIR /ng-app

# Copy the current directory contents into the container at /app
ADD ./dist /ng-app/dist

### STAGE 2: Setup ###

FROM nginx:1.17.0-alpine

## Copy our default nginx config
COPY nginx/default.conf /etc/nginx/conf.d/

## Copy our gzip nginx config
COPY nginx/gzip.conf /etc/nginx/conf.d/

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## From 'builder' stage copy over the artifacts in dist folder to default nginx public folder
COPY --from=builder /ng-app/dist/onco-web /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]
