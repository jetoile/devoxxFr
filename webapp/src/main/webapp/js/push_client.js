/*  
 * Copyright (c) 2011 Ant Kutschera
 * 
 * This file is part of Ant Kutschera's blog, 
 * http://blog.maxant.co.uk
 * 
 * This is free software: you can redistribute
 * it and/or modify it under the terms of the
 * Lesser GNU General Public License as published by
 * the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * It is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the Lesser GNU General Public License for
 * more details. 
 * 
 * You should have received a copy of the
 * Lesser GNU General Public License along with this software.
 * If not, see http://www.gnu.org/licenses/.
 */

function PushClient(ch, m){

	this.channel = ch;
	this.ajax = getAjaxClient();
	this.onMessage = m;

	// stick a reference to "this" into the ajax client, so that the handleMessage 
	// function can access the push client - its "this" is an XMLHttpRequest object
	// rather than the push client, coz thats how javascript works!
	this.ajax.pushClient = this;
	
	function getAjaxClient(){
		/*
		 * Gets the ajax client
		 * http://en.wikipedia.org/wiki/XMLHttpRequest
		 * http://www.w3.org/TR/XMLHttpRequest/#responsetext
		 */
	    var client = null;
	    try{
			// Firefox, Opera 8.0+, Safari
			client = new XMLHttpRequest();

            if ("withCredentials" in client){
                client.open("get", "http://localhost:8080/webapp-1.0.1-SNAPSHOT/servletAsync", true);
                alert('ici');
            } else if (typeof XDomainRequest != "undefined"){
//                client = new XDomainRequest();
//                client.open(method, url);
                alert('la');
            } else {
                alert('nan');
                client = null;
            }

		}catch (e){
			// Internet Explorer
			try{
				client = new ActiveXObject("Msxml2.XMLHTTP");
			}catch (e){
				client = new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
		return client;
	};
	
	/** 
	 * pass in a callback and a channel.  
	 * the callback should take a string, 
	 * which is the latest version of the model 
	 */
	PushClient.prototype.login = function(){

		try{
			var params = escape("channel") + "=" + escape(this.channel);
			var url = "http://localhost:8080/webapp-1.0.1-SNAPSHOT/servletAsync?" + params;
			this.ajax.onreadystatechange = handleMessage;
			this.ajax.open("GET",url,true); //true means async, which is the safest way to do it
			
			// hint to the browser and server, that we are doing something long running
			// initial tests only seemed to work with this - dont know, perhaps now it 
			// works without it?
			this.ajax.setRequestHeader("Connection", "Keep-Alive");
			this.ajax.setRequestHeader("Keep-Alive", "timeout=999, max=99");
			this.ajax.setRequestHeader("Transfer-Encoding", "chunked");
//            this.ajax.setRequestHeader("Access-Control-Allow-Origin", "*");
			
			//send the GET request to the server
			this.ajax.send(null);
		}catch(e){
			alert(e);
		}
	};

	function handleMessage() {
		//states are:
		//	0 (Uninitialized)	The object has been created, but not initialized (the open method has not been called).
		//	1 (Open)	The object has been created, but the send method has not been called.
		//	2 (Sent)	The send method has been called. responseText is not available. responseBody is not available.
		//	3 (Receiving)	Some data has been received. responseText is not available. responseBody is not available.
		//	4 (Loaded)
		try{
			if(this.readyState == 0){
				//this.pushClient.onMessage("0/-/-");
			}else if (this.readyState == 1){
				//this.pushClient.onMessage("1/-/-");
			}else if (this.readyState == 2){
				//this.pushClient.onMessage("2/-/-");
			}else if (this.readyState == 3){
				//for chunked encoding, we get the newest version of the entire response here, 
				//rather than in readyState 4, which is more usual.
				if (this.status == 200){
					this.pushClient.onMessage("3/200/" + this.responseText.substring(this.responseText.lastIndexOf("|")));
				}else{
					this.pushClient.onMessage("3/" + this.status + "/-");
				}
			}else if (this.readyState == 4){
				if (this.status == 200){
					
					//the connection is now closed.
					
					this.pushClient.onMessage("4/200/" + this.responseText.substring(this.responseText.lastIndexOf("|")));

					//start again - we were just disconnected!
//					this.pushClient.login();

				}else{
					this.pushClient.onMessage("4/" + this.status + "/-");
				}
			}
		}catch(e){
			alert(e);
		}
	};
}