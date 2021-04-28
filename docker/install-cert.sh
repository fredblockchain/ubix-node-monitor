wget -t0 -c http://crl.usertrust.com/USERTrustRSACertificationAuthority.crl
mv ./USERTrustRSACertificationAuthority.crl /usr/local/share/ca-certificates/USERTrustRSACertificationAuthority.crl
sudo update-ca-certificates
