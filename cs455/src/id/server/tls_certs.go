package main

var certBlock []byte
var keyBlock []byte

func tlsInit() {
	certBlock = []byte(`-----BEGIN CERTIFICATE-----
Certificate Suppressed
-----END CERTIFICATE-----`)
	keyBlock = []byte(`-----BEGIN PRIVATE KEY-----
Key Suppressed
-----END PRIVATE KEY-----`)

}
