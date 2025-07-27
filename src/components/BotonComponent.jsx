import { useEffect, useState } from "react";
import { useFetch } from "../Hooks/useFetch";

export const BotonComponent = ({btnName="",texto,sData,sBoton,url}) => {
    const [shouldFetch,setShouldFetch] = useState(false)
    
    const data = useFetch(url,texto,shouldFetch)
    useEffect(()=>{
      if(data.listo){
        sData(data)
        setShouldFetch(false)
      }
    },[data])
    const onClick =()=>{
        setShouldFetch(true)
        sBoton(btnName)
      }
  return (
    <button type="button" 
    className="btn btn-outline-secondary"
    onClick={onClick}>{btnName}</button>
  )
}
