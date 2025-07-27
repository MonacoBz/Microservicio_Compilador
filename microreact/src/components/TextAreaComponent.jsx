import React, { useEffect, useState } from 'react'

export const TextAreaComponent = ({setT}) => {
    const textV = "public class Main{\n     public static void main(String[] args){\n       int entero = 10;\n     } \n}"
    const [text,setText] = useState(textV)
    const onChange=(event)=>{
        setText(event.target.value);
    }
    useEffect(()=>{
        setT(text)
    },[text])

  return (
    <div id="HijoT"> 
        <h1>Compilador</h1>
        <textarea 
            className="form-control" 
            placeholder="Leave a comment here" 
            id="floatingTextarea" 
            style={{ height: '250px' }}
            value={text}
            onChange={onChange}></textarea>
            
        
    </div>
  )
}
