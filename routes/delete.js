const router=require('express').Router();
const data=require('../models/dataSchema');

router.post("/:id",(req,res)=>{
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

module.exports=router;
