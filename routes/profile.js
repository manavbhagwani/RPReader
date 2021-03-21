const router=require('express').Router();
const authrouter=require("./auth");

router.get("/",authrouter,(req,res)=>{
    res.send("under construction");
});

module.exports=router
