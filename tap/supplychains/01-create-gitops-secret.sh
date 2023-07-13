#!/usr/bin/env bash

SSH_FOLDER=~/.ssh
export SSH_PRIVATE_KEY=$(cat $SSH_FOLDER/id_rsa)
export SSH_IDENTITY=$(cat $SSH_FOLDER/id_rsa)
export SSH_IDENTITY_PUB=$(cat $SSH_FOLDER/id_rsa.pub)
export SSH_KNOWN_HOSTS=$(cat $SSH_FOLDER/known_hosts)

ytt -f secret-template.yaml --data-value sshprivatekey="$SSH_PRIVATE_KEY" --data-value identity="$SSH_IDENTITY"  --data-value identitypub="$SSH_IDENTITY_PUB" --data-value knownhosts="$SSH_KNOWN_HOSTS" | kubectl apply -f-