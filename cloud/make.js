fs = require('fs');

// MAIN
let foobar = makeRandomDataSet(16,16);
fs.writeFile('data.csv', foobar, function (err) {
  if (err) return console.log(err);
});


// HELPER
function makeRandomDataSet(length1, length2){
	let index = 1;
	let headers = makeHeaderList(length1)
	let hsplit = headers.split(",");
	let data = index;
	for(let i = 0; i < length2; i++){
		data = index;
		for(let ii = 0; ii < hsplit.length; ii++){
			if(ii != 0){
				data = data + ",";
				data = data + Math.floor(-10000 + Math.random() * 20000);
			}
		}
		headers = headers + data + "\n";
		index = index + 1;
	}
	return headers;
}

function makeHeaderList(length){
	let list = "ID";
	if(length > 1){
		for(let i = 0; i < length; i++){
			list = list + ",";
			let hItem = makeRandomString(4);
			while(list.includes(hItem) || !isNaN(parseInt(hItem.charAt(0)))){
				hItem = makeRandomString(4);
			}
			list = list + hItem;
		}
	}
	return list+"\n";
}


function makeRandomString(length) {
   var result           = '';
   var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
   var charactersLength = characters.length;
   for ( var i = 0; i < length; i++ ) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
   }
   return result;
}
