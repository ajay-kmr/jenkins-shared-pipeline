##Creating A Local Docker Registry ##
*****
**Refer:-** https://docs.docker.com/registry/deploying/
___
####Native basic auth ####
The simplest way to achieve access restriction is through basic authentication (this is very similar to other web servers’ basic authentication mechanism).
<br>
This example uses native basic authentication using htpasswd to store the secrets.
<br>
1. Create a password file with one entry for the user testuser, with password testpassword:
    ```shell script
         mkdir auth
   
         docker run \
          --entrypoint htpasswd \
          registry:2 -Bbn testuser testpassword > auth/htpasswd
     ```
   The above command will add the credentials (username:- **testuser** and password **testpassword**) to file **auth/htpasswd** which can be used to login 

2. Stop the registry.
    ```shell script 
        $ docker container stop registry
   ```
3. Start the registry with basic authentication.
    ```shell script    
        $ docker run -d \
          -p 5000:5000 \
          --restart=always \
          --name registry \
          -v "$(pwd)"/auth:/auth \
          -e "REGISTRY_AUTH=htpasswd" \
          -e "REGISTRY_AUTH_HTPASSWD_REALM=Registry Realm" \
          -e REGISTRY_AUTH_HTPASSWD_PATH=/auth/htpasswd \
          -v "$(pwd)"/certs:/certs \
          -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
          -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
          registry:2
    ```
   **Note:-** <br>
     >1. The enrironment variables **REGISTRY_HTTP_TLS_CERTIFICATE** and **REGISTRY_HTTP_TLS_KEY** can avoided in case no certificate is availabe

4. Try to pull an image from the registry, or push an image to the registry. These commands fail.

5. Log in to the registry.
    ```shell script
       $ docker login myregistrydomain.com:5000    
    ```
    Provide the username and password from the first step (i.e (username:- **testuser** and password **testpassword**) ).

    Test that you can now pull an image from the registry or push an image to the registry.   

****

###Add UI for Docker Registry ###
**Refer:-**
1. https://hub.docker.com/r/joxit/docker-registry-ui/
2. https://github.com/Joxit/docker-registry-ui/tree/master/examples/ui-as-proxy     