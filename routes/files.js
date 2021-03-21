const router=require('express').Router();
const authrouter=require("./auth");
const data=require('../models/dataSchema');
const jwt=require('jsonwebtoken');
const fs = require('fs');

router.get("/",authrouter,(req,res)=>{
    var decode=jwt.verify(req.cookies.token,"msdhoni");
    res.render("files",{username:decode.username});
});

router.post("/",authrouter,(req,res)=>
{
    var a=req.body.text.split("\n");
    var format=a[a.length-1];
    var title="";
    var author=[];
    var affiliation=[];
    var email=[];
    var abstract;
    var keyword;
    for(let j=0;j<a.length;j++)
    {
        if(a[j].includes("title"))
        {
            title=a[j].substring(a[j].indexOf(':')+1).trim();
            break;
        }
    }
    for(var i=0;i<a.length;i++)
    {
        if(a[i].includes("Author"))
            author.push(a[i].substring(a[i].indexOf(':')+1).trim());
        if(a[i].includes("Affiliation"))
            affiliation.push(a[i].substring(a[i].indexOf(':')+1).trim());
        if(a[i].includes("Email"))
            email.push(a[i].substring(a[i].indexOf(':')+1).trim());
    }
    for(let i=0;i<a.length;i++)
    {
        if(a[i].toLowerCase().includes("abstract"))
            abstract=a[i];
        if(a[i].toLowerCase().includes("keyword") || a[i].toLowerCase().includes("index terms"))
            keyword=a[i];
    }
    var files = fs.readdirSync('./uploads');
    files.sort();
    var path="/uploads/"+files[files.length-1];
    data.find({Abstract:abstract},function(err,data1)
    {
        if(err)
            res.send("Something went wrong");
        else
        {
            if(data1.length==0)
            {
                data.create({
                    Format:format,
                    Title:title,
                    Author:author,
                    Affiliation:affiliation,
                    Email:email,
                    Abstract:abstract,
                    Keyword:keyword,
                    Path:path
                });
                res.send("Success");
            }
        else
            res.send("File contents already in the database");
        }
    });
});

module.exports=router
