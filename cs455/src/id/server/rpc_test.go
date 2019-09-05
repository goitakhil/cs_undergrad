package main

import (
	"crypto/sha256"
	"crypto/tls"
	"crypto/x509"
	"encoding/gob"
	"errors"
	"fmt"
	. "id/interfaces"
	"math/rand"
	"net/rpc"
	"os"
	"os/exec"
	"testing"
	"text/tabwriter"
	"time"

	log "github.com/sirupsen/logrus"
)

var client *rpc.Client
var uname string
var src = rand.NewSource(time.Now().UnixNano())

const letterBytes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
const (
	letterIdxBits = 6                    // 6 bits to represent a letter index
	letterIdxMask = 1<<letterIdxBits - 1 // All 1-bits, as many as letterIdxBits
	letterIdxMax  = 63 / letterIdxBits   // # of letter indices fitting in 63 bits
)

func TestRPC(t *testing.T) {
	log.SetLevel(log.ErrorLevel)

	cmd := exec.Command("redis", "--port 5186")
	if err := cmd.Start(); err != nil {
		fmt.Printf("ERROR Unable to run %v: %s", cmd, err.Error())
	} else {
		log.Info("Started up Redis")
		redisProcess = cmd
		// Wait for redis to spin up
		time.Sleep(time.Second * 5)
	}
	defer cmd.Process.Kill()

	var err error
	database, err = NewUserDB("127.0.0.1:5186")
	if err != nil {
		log.WithFields(log.Fields{
			"error": err,
		}).Error("An error occured while connecting to Redis.")
		os.Exit(1)
	}
	log.Info("Connected to Redis")

	_, err = database.IDmaps.Do("FLUSHALL")
	errorCheck(t, err)
	go RPCMain(5185)
	time.Sleep(time.Second)

	err = connect("127.0.0.1", "5185")
	errorCheck(t, err)
	var result string
	err = client.Call("RPCServer.Ping", 0, &result)
	errorCheck(t, err)
	err = sendCreate("testuser", "Real Name", "Passw0rd!")
	errorCheck(t, err)
	err = sendCreate("testuser", "Real Name", "Passw0rd!")
	if err == nil {
		t.Fail()
	}
	err = sendLookup("testuser")
	errorCheck(t, err)
	err = sendReverse(id)
	errorCheck(t, err)
	err = sendModify("testuser", "renameduser", "Badpass")
	if err == nil {
		t.Fail()
	}
	err = sendModify("testuser", "renameduser", "Passw0rd!")
	errorCheck(t, err)
	uname = "renameduser"
	err = sendLookup("renameduser")
	errorCheck(t, err)
	err = sendReverse(id)
	errorCheck(t, err)
	err = sendDelete("renameduser", "Badpass")
	if err == nil {
		t.Fail()
	}
	err = sendDelete("renameduser", "Passw0rd!")
	errorCheck(t, err)
	err = getUsers()
	errorCheck(t, err)
	err = getUUIDs()
	errorCheck(t, err)
	err = getAll()
	errorCheck(t, err)
}

func errorCheck(t *testing.T, err error) {
	if err != nil {
		t.Error(err)
	}
}

func connect(server, port string) error {
	var err error
	const rootPEM = `-----BEGIN CERTIFICATE-----
MIIFfDCCA2SgAwIBAgIJANQSl5//ATQ/MA0GCSqGSIb3DQEBCwUAMFMxCzAJBgNV
BAYTAlhYMRUwEwYDVQQHDAxEZWZhdWx0IENpdHkxHDAaBgNVBAoME0RlZmF1bHQg
Q29tcGFueSBMdGQxDzANBgNVBAMMBnRlYW0xOTAeFw0xODA0MTYyMDQ2MzVaFw0x
OTA0MTYyMDQ2MzVaMFMxCzAJBgNVBAYTAlhYMRUwEwYDVQQHDAxEZWZhdWx0IENp
dHkxHDAaBgNVBAoME0RlZmF1bHQgQ29tcGFueSBMdGQxDzANBgNVBAMMBnRlYW0x
OTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAL++W5oyeixYXgspZjlZ
eYps7lYzfmTv7fuCTvXAjd8aWT5bnvQz2mep2uG2LjAPiTYcmVH3XhI8EKIXglc7
L2g21ERbp+/KCNE+WCXy7QtrKG49p5Pma5ud766UsK5SrQifvp+Oy1ZStBeEFKWz
yPkWhiueguX3Dql/goFAPBOp6vNHA2GdNjLjfO+f34ktrIugpWeYOYyEQH8EqzBu
IbJd9nnocredZ6htMOxDWeXKBdBpVM2VH4/mTmiYBHO20QcZpswno6rFP6Ngy3TX
50/WTwCfBuuVtP4Wt0+AhMYdNZ4wS6ov1Cvh24/5yXMVM4v+grXsDt+KYDAKkP4+
QhieHRTRxnN/JbiKMheUljMwYRGzDaPpRexYjOYe1mvx6X/DpgqOwLzxiHuy42/n
RNVNC+Np1FDz18223Nl8ZTapixHl4E0eRCmoBRWd8qCbR6bLncmUWfw/vpVYD3aL
LN6D6uh8EGWspu/cVZmIJkh6tqC96Abz5e1ug9gS93Rl4NiiQN64QAi5rNnp+Pcs
tvofAsClPUlDYU+NSNUGCQYB4YfIjrd4KPMUe8aAuW4NPxkWZrh783y3RXwii5uY
0gW3lI+VERYVajPqBaAWC4VSkTLNm4jQv6T1qn59a9yOJ8tdJ/Z7OCB/kclZ1PzF
HKa5LR88NOUNb+bE1lk64OKzAgMBAAGjUzBRMB0GA1UdDgQWBBS/GROEWMycZqxm
ZkWvfFP4d1L6YjAfBgNVHSMEGDAWgBS/GROEWMycZqxmZkWvfFP4d1L6YjAPBgNV
HRMBAf8EBTADAQH/MA0GCSqGSIb3DQEBCwUAA4ICAQCshoFNfWUaroNWZLGPFwRQ
hcExyEbUEUjDMPJK9JRNIangA+6wrcQ46K5lZe7XnB09FiICZKGqjeBcvnZ0mWcY
zNv/Gb+n/x+vz0TXDMCRcvBrQ/M/3laSoOGYNeA67JkiZLp6HZ5COl5kQywF4wPn
lPw54Xlh1WYNZrniSXUTOz6SQX0bX0Zlkuf3QbI5GK0AATcxJVxaoK/B7Tf3mhTD
CkVqKij74p/M8EmfDKlHxt/54VPakCIvKnORNs35IYD9UpHs1nATGtow6kZSAGkv
xSxvOSqMc+E29hf1Nwflvfmu2vJD1CUXRN/S76OUfyLLBG4stXBich54iq7OS//9
DcN1zshI7pwlRESyUsYCrjfEmEuALRN0EU5b8W7iZE28f3Y4NkxLu1QrMeNnpaIN
EnQVMt2i1hK6Is46TIAiTABbQCfs5jCu0ZRUeaxx/i2IyOE91wkquCjtK811Paj4
0DeAAHyPsscimTt5pOl4sxEDDZJpggC5/DfpzUC6aG3+AQ41nZXeZPZZcqAJ2VKT
o4e/B2IfcQyhGXZYGriDSUS4gbjOfSkPFEbnlpXzpMkJzE0ysSEEmF2qsOK5l2+l
zJpneCDG5N5J64d4kkfVL3hMJJp0h3/TgUeiRAQwOrSCN9/MUdvHRHNDfP9U+UrL
skIioGnu999WRj4jwplDsQ==
-----END CERTIFICATE-----`

	roots := x509.NewCertPool()
	ok := roots.AppendCertsFromPEM([]byte(rootPEM))
	if !ok {
		panic("failed to parse root certificate")
	}

	conn, err := tls.Dial("tcp", server+":"+port, &tls.Config{
		RootCAs:    roots,
		ServerName: "team19",
	})
	if err != nil {
		return err
	}
	var ip string
	gob.NewDecoder(conn).Decode(&ip)
	client = rpc.NewClient(conn)
	return nil
}

func sendCreate(username, realname, password string) error {

	pass := sha256.Sum256([]byte(password))
	args := CreateArgs{
		Username: username,
		Realname: realname,
		Password: pass,
		IPAddr:   "127.0.0.1",
		Initial:  true,
	}
	var record string
	err := client.Call("RPCServer.Create", args, &record)
	if err != nil {
		return err
	}
	uname = username
	id = record

	return nil
}

func sendLookup(username string) error {
	var result Record
	err := client.Call("RPCServer.Lookup", username, &result)
	if err != nil {
		return err
	}
	if id == result.UUID && uname == result.Username {
		return nil
	}
	fmt.Printf("expected: %s \nactual: %s", id, result.UUID)
	fmt.Printf("expected: %s \nactual: %s", uname, result.Username)
	return errors.New("Result Mismatch")

}
func sendReverse(uuid string) error {
	var result Record
	err := client.Call("RPCServer.ReverseLookup", uuid, &result)
	if err != nil {
		return err
	}
	if id == result.UUID && uname == result.Username {
		return nil
	}
	fmt.Printf("expected: %s \nactual: %s", id, result.UUID)
	fmt.Printf("expected: %s \nactual: %s", uname, result.Username)
	return errors.New("Result Mismatch")
}
func sendModify(username, newusername, password string) error {
	pass := sha256.Sum256([]byte(password))
	args := ModifyArgs{
		OldUsername: username,
		NewUsername: newusername,
		Password:    pass,
		IPAddr:      "127.0.0.1",
		Initial:     true,
	}
	var success string
	err := client.Call("RPCServer.Modify", args, &success)
	return err
}

func sendDelete(username, password string) error {
	pass := sha256.Sum256([]byte(password))
	args := DeleteArgs{
		Username: username,
		Password: pass,
		Initial:  true,
	}
	var success string
	err := client.Call("RPCServer.Delete", args, &success)
	return err
}

func getUsers() error {
	fmt.Println("Getting Userlist")
	var result []string
	err := client.Call("RPCServer.GetUsers", 0, &result)
	if err != nil {
		return err
	}
	for _, v := range result {
		fmt.Println(v)
	}
	return nil
}

func getUUIDs() error {
	fmt.Println("Getting UUID List")
	var result []string
	err := client.Call("RPCServer.GetUUIDS", 0, &result)
	if err != nil {
		return err
	}
	for _, v := range result {
		fmt.Println(v)
	}
	return nil
}

func getAll() error {
	fmt.Println("Getting All Records")

	var result []Record
	err := client.Call("RPCServer.GetAll", 0, &result)
	if err != nil {
		return err
	}

	w := tabwriter.NewWriter(os.Stdout, 0, 0, 1, ' ', tabwriter.Debug)

	for _, v := range result {
		fmt.Fprintln(w, v.Username+"\t"+v.Realname+"\t"+v.CreatedBy+"\t"+v.UUID)
	}
	w.Flush()
	return nil
}

func BenchmarkLookup(b *testing.B) {
	connect("127.0.0.1", "5185")

	pass := sha256.Sum256([]byte("password"))
	args := CreateArgs{
		Username: "testuser",
		Realname: "Name",
		Password: pass,
		IPAddr:   "127.0.0.1",
		Initial:  true,
	}
	var record string
	client.Call("RPCServer.Create", args, &record)

	b.ResetTimer()
	b.RunParallel(func(pb *testing.PB) {
		var result Record
		for pb.Next() {
			client.Call("RPCServer.Lookup", "testuser", &result)
		}
	})

}

func BenchmarkCreate(b *testing.B) {
	connect("127.0.0.1", "5185")

	b.ResetTimer()
	b.RunParallel(func(pb *testing.PB) {
		pass := sha256.Sum256([]byte("password"))
		args := CreateArgs{
			Username: "testuser",
			Realname: "Name",
			Password: pass,
			IPAddr:   "127.0.0.1",
			Initial:  true,
		}
		var record string
		for pb.Next() {
			args.Username = RandStringBytesMaskImprSrc(16)
			client.Call("RPCServer.Create", args, &record)
		}
	})

}

func RandStringBytesMaskImprSrc(n int) string {
	b := make([]byte, n)
	// A src.Int63() generates 63 random bits, enough for letterIdxMax characters!
	for i, cache, remain := n-1, src.Int63(), letterIdxMax; i >= 0; {
		if remain == 0 {
			cache, remain = src.Int63(), letterIdxMax
		}
		if idx := int(cache & letterIdxMask); idx < len(letterBytes) {
			b[i] = letterBytes[idx]
			i--
		}
		cache >>= letterIdxBits
		remain--
	}

	return string(b)
}
