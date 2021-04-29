wget -t0 -c http://crl.usertrust.com/USERTrustRSACertificationAuthority.crl
mv ./USERTrustRSACertificationAuthority.crl /usr/local/share/ca-certificates/USERTrustRSACertificationAuthority.crt
sudo update-ca-certificates
