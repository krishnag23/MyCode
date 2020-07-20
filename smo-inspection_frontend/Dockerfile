# stage 1

FROM node:12.16.2-alpine AS webapp
WORKDIR /app
COPY . .
# RUN npm install && npm run build

# stage 2

FROM nginx:alpine
RUN rm /etc/nginx/conf.d/*
ADD server.conf.template /etc/nginx/conf.d/

RUN apk --no-cache add curl
RUN apk update && apk add busybox-extras


COPY --from=webapp /app/dist/smo-inspection /usr/share/nginx/html
RUN chmod -R 777 /usr/share/nginx/html/assets

COPY deploy.sh /
RUN chmod 777 deploy.sh
ENTRYPOINT ["/deploy.sh"]
CMD ["nginx", "-g", "daemon off;"]

EXPOSE 80
