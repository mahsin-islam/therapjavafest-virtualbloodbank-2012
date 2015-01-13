/*
checking the strength of password
*/
function checkPasswordStrength(){
	var userPassword = document.getElementById("passwordNewPassword").value;
	var security = 0;
	var status = "";
	var colorName = "";
	
	//length
	if( userPassword.length>=6 )
		security++;
	//a to z
	if( (/[a-z]/.test(userPassword))==true )
		security++;
	//A to Z
	if( (/[A-Z]/.test(userPassword))==true )
		security++;
	//0 to 9
	if( (/[0-9]/.test(userPassword))==true )
		security++;
	//punchuation
	for(var I=0; I<userPassword.length; I++){
		var ch = userPassword[I];
		if( (ch<'a'||ch>'z')&&(ch<'A'||ch>'Z')&&(ch<'0'||ch>'9') ){
			security++;
			break;
		}
	}
	
	//show status
	if( security<=1 ){
		colorName = "red";
		status = "Very poor";
	}
	else if( security==2 ){
		colorName = "orange";
		status = "Poor";
	}
	else if( security==3 ){
		colorName = "magenta";
		status = "Good";
	}
	else if( security==4 ){
		colorName = "blue";
		status = "Strong";
	}
	else if( security==5 ){
		colorName = "green";
		status = "Very strong";
	}
	document.getElementById("newPass3rdCol").innerHTML = "<font color="+colorName+">"+status+"</font>";
}

/*
checking password is match or not in confirm password
*/
function checkPasswordMatch(){
	var newPassword = document.getElementById("passwordNewPassword").value;
	var conPassword = document.getElementById("passwordConfirmPassword").value;
	var colorName = "";
	var status = "";
	
	if( newPassword==conPassword ){
		colorName = "green";
		status = "Matched";
	}
	else{
		colorName = "red";
		status = "Not matched";
	}
	document.getElementById("conPass3rdCol").innerHTML = "<font color="+colorName+">"+status+"</font>";
}

/*
check var is finite number
*/
function isNumber(N) {
	return !isNaN(parseFloat(N)) && isFinite(N);
}

/*
check number is valid or not
*/
function checkNumber(){
	var number = document.getElementById("textMobileNumber").value;
	var colorName = "";
	var status = "";
	
	if( number.length==11 && isNumber(number) ){
		colorName = "green";
		status = "Valid";
	}
	else{
		colorName = "red";
		status = "Invalid";
	}
	document.getElementById("mobNo3rdCol").innerHTML = "<font color="+colorName+">"+status+"</font>";
}