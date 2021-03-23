const router=require('express').Router();
const authrouter=require("./auth");
const data=require('../models/dataSchema');
const jwt=require('jsonwebtoken');

router.get("/:id",authrouter,(req,res)=>{
	var decode=jwt.verify(req.cookies.token,"msdhoni");
	data.find({$and:[{_id:req.params.id},{User:decode.email}]},function(err,data1){
        if(err)
            res.send("incorrect");
        else
            res.render("details",{username:decode.username,det:data1[0]});
    });
});

router.post("/:id",authrouter,(req,res)=>{
	var decode=jwt.verify(req.cookies.token,"msdhoni");
	const result=data.deleteOne({$and:[{_id:req.params.id},{User:decode.email}]}).exec()
	res.send("successful");
});


module.exports=router;
