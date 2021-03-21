var btn=document.querySelector(".btn");
btn.addEventListener("click",async (e)=>{
    e.preventDefault();
    const res=await axios.post(window.location.href,{
    });
    if(res.data=="successful")
    {
        document.querySelector(".togg1").classList.remove("togg");
        document.querySelector(".module").classList.add("togg");
        document.querySelector("body").addEventListener("click",(e)=>{
            window.location.href="/files";
        });
    }
});
