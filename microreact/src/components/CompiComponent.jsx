import React from 'react'

export const CompiComponent = ({json}) => {
    console.log(json.json.Compilador)
  return (
     <textarea 
            readOnly={true} 
            className="form-control" 
            style={{ height: '400px',width: '500px' }}
            value={json.json.Compilador}
            ></textarea>
  )
}
