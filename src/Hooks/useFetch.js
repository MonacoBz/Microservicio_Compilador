import { useEffect, useState } from "react"

export const useFetch = (url,text,shouldFetch) => {
  const [json,setJson] = useState({json:null,listo:false});
  useEffect(()=>{
    const getJson=async()=>{
        if(!shouldFetch){
          setJson({json:null,listo:false})
          return; 
        }
        const response = await fetch(url,{
                        method:"post",
                        body:text
                    });
        const data = await response.json();
        setJson({
            json:data,
            listo:true
        })
    }
    getJson();
  },[shouldFetch,url])
    return json
}
