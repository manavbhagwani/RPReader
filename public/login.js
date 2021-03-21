var current=0;
const btn=document.querySelector(".btn");
btn.addEventListener("click",async (e)=>{
    e.preventDefault();
    let flag=check(document.querySelector("#email"),4) && valid(document.querySelector("#email"),4) && check(document.querySelector("#password"),5);
    if(flag)
    {
        const res=await axios.post("/login",{
            email:document.querySelector("#email").value,
            password:document.querySelector("#password").value
        });
        if(res.data=="incorrect")
        {
            let curr=1;
            if(curr!=current)
            {
                field=document.querySelector("#password");
                span=document.createElement("span");
                span.textContent="Either email or password is incorrect";
                span.classList.add("remove");
                field.after(span);
                current=curr;
            }
        }
        else
            window.location.pathname="/";
    }
});
function check(field,curr){
    let flag=onlyspace(field.value);
    if(!flag && curr!=current)
    {
        span=document.createElement("span");
        span.textContent="Fill in the required field";
        span.classList.add("remove");
        field.after(span);
        current=curr;
        return false;
    }
    if(!flag)
        return false;
    return true;
}
function onlyspace(str){
    for(i in str)
    {
        if(str[i]!=' ')
            return true;
    }
    return false;
}
function special1(field,curr,size)
{
    let flag=(field.value.length>=size)?true:false;
    if(!flag && curr!=current)
    {
        span=document.createElement("span");
        span.textContent="Minimum length should be 6";
        span.classList.add("remove");
        field.after(span);
        current=curr;
        return false;
    }
    if(!flag)
        return false;
    return true;
}
function valid(field,curr)
{
    let flag=field.value.includes("@");
    if(!flag && curr!=current)
    {
        span=document.createElement("span");
        span.textContent="The field value is invalid";
        span.classList.add("remove");
        field.after(span);
        current=curr;
        return false;
    }
    if(!flag)
        return false;
    return true;
}
const ael=document.querySelectorAll("input");
for(let i=0;i<ael.length;i++)
{
    ael[i].addEventListener("click",clearscreen);
}
function clearscreen()
{
    current=0;
    var arr=document.querySelectorAll(".remove");
    for(let i=0;i<arr.length;i++)
    {
        arr[i].remove();
    }
}
