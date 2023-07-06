#!/usr/bin/env bash

echo "REDIS - tanzu service class-claim create redis-1 --class redis-unmanaged"
tanzu service class-claim create redis-1 --class redis-unmanaged

echo "POSTGRESQL - tanzu service class-claim create postgres-1 --class postgresql-unmanaged"
tanzu service class-claim create postgres-1 --class postgresql-unmanaged

sleep 20

echo "CHECKS - tanzu service class-claim list"
tanzu service class-claim list

echo "DONE"