const router=require('express').Router();
const authrouter=require("./auth");
const data=require('../models/dataSchema');
const jwt=require('jsonwebtoken');

router.get("/:id",authrouter,(req,res)=>{
	data.find({_id:req.params.id},function(err,data1){
        if(err)
            res.send("incorrect");
        else
        {
            var decode=jwt.verify(req.cookies.token,"msdhoni");
            res.render("details",{username:decode.username,det:data1[0]});
        }
    });
});

router.post("/:id",authrouter,(req,res)=>{
	const result=data.deleteOne({_id:req.params.id}).exec()
	res.send("successful");
});


module.exports=router;
