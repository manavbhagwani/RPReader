const router=require('express').Router();
const data=require('../models/dataSchema');


router.get("/",(req,res)=>{
	data.find({},function(err,data1){
        if(err)
            res.send("incorrect");
        else
        {
            res.send(data1);
        }
    });
});

module.exports=router;
