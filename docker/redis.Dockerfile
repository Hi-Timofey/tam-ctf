FROM redis:6.2-bullseye

#COPY ./docker/redis.conf /etc/redis.conf

CMD [ "redis-server", "/etc/redis.conf" ]
