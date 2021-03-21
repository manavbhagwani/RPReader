const express = require('express');
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
const jwt = require('jsonwebtoken');
const mongoose = require('mongoose');
const {exec} = require('child_process');
const path = require('path');
const homerouter=require('./routes/home');
const loginrouter=require('./routes/login');
const logoutrouter=require("./routes/logout");
const filesrouter=require("./routes/files")
const profilerouter=require("./routes/profile")
const downloadrouter=require("./routes/download")
const detailsrouter=require("./routes/details")


require('dotenv').config();


mongoose.connect(process.env.key, {
	useNewUrlParser: true,
	useCreateIndex: true,
    useUnifiedTopology: true
}).then(() => {
	console.log('Connected to DB!');
}).catch(err => {
	console.log('ERROR:', err.message);
});


const app=express();
app.set("view engine","ejs");


app.use(express.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static("public"));

app.use("/",homerouter);
app.use("/login",loginrouter);
app.use("/logout",logoutrouter);

app.use("/files",filesrouter);
app.use("/profile",profilerouter);
app.use("/download",downloadrouter);
app.use("/details",detailsrouter);

app.get("/downloadpdf/uploads/:pdf",(req,res)=>{
	res.sendFile(path.join(__dirname, "./uploads/"+req.params.pdf));
})




app.listen(3000,"0.0.0.0",()=>{
    console.log("RUNNING");
});
