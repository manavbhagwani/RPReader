const router=require('express').Router();
const data=require('../models/dataSchema');
const authrouter=require("./auth");
const jwt = require('jsonwebtoken');


router.get("/",authrouter,(req,res)=>{
	var decode=jwt.verify(req.cookies.token,"msdhoni");
	data.find({User:decode.email},function(err,data1){
        if(err)
            res.send("incorrect");
        else
        {
            res.send(data1);
        }
    });
});

module.exports=router;
