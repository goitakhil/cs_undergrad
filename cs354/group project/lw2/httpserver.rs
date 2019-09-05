
use std::io::{Read, Write, BufReader, BufRead};
use std::net::{TcpListener, TcpStream};
use std::fs::File;

fn handle_client(mut stream: TcpStream) {
	//Buffer the input so we know when the browser is done sending.
	let mut reader = BufReader::new(stream);
	let mut request: String = "".to_string();
	
	//Read the input by lines and strip CR characters. HTTP is a Windows-style standard.
    for line in reader.by_ref().lines() {
		let q = line.unwrap();
		let s = q.trim_matches('\r');
        if s == "" {
            break;
        } else {
			//Add a space for the split later on.
			let r = s.to_string() + " ";
			request.push_str(&r);
		}
    }
    
    //Get stream back from the buffer.
	stream = reader.into_inner();
	//Tokenize the request
    let mut reply: String = "".to_string();
    let tokens: Vec<&str> = request.split(" ").collect();
    //Check if the request is valid
    if tokens[0] == "GET" {
        if tokens[2] == "HTTP/1.1" {
			let filename: String;
			if tokens[1] == "/" {
				filename = "/index.html".to_owned();
			} else if !tokens[1].contains("."){
				filename = format!("{}{}", tokens[1], ".html");
			} else {
				filename = tokens[1].to_string();
			}
			println!("{}", filename);
			//Open the file reqested. Requests should not have an explicit .html.
			let mut f;
            match File::open(format!(".{}", filename)) {
				//Handle error by throwing 404.
				Err(e) => {  println!("{}", e.to_string());
							 let _ = stream.write_all(b"HTTP/1.1 404 Not Found\n\n");
							 let _ = stream.write_all(b"<!DOCTYPE HTML><html><head><title>404 - this page does not exist</title></head><h1>404 - This Page does not exist</h1></html>");
							 let _ = stream.flush();
							 return;
							 }
				//Else, return file handle.
				Ok(file) => {f = file;}
			};
			
			//Read in the file and stream it to the browser.
			//The underscores are our way of telling rust to ignore errors.
			//We should actually check them for real code.
            let _ = f.read_to_string(&mut reply);
            let v = reply.into_bytes();
            let _ = stream.write_all(b"HTTP/1.1 200 OK\n\n");
            let _ = stream.write_all(&v);
        }
    }
    //Make sure we pushed all the data before we close the connection.
	let _ = stream.flush();
}

fn main(){
	let listener = TcpListener::bind("0.0.0.0:8888").unwrap();
	//Accept connections and process them, spawning a new thread for each one
	for stream in listener.incoming() {
		match stream {
			Ok(stream) => {
				handle_client(stream);
			}
			Err(_) => { /* connection failed */ }
		}
	}
}
