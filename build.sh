#!/usr/bin/env bash

. env.sh

./mvnw -q clean package -T 4
