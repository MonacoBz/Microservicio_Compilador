import { useEffect, useState } from 'react';
import { TextAreaComponent } from './components/TextAreaComponent';
import { BotonComponent } from './components/BotonComponent';
import { TokensComponent } from './components/TokensComponent';
import { CompiComponent } from './components/CompiComponent';

export const PaginaPrincipal = () => {
  const [texto, setTexto] = useState("");

  const [currentResponseData, setCurrentResponseData] = useState(null); 
  const [lastActionType, setLastActionType] = useState(""); 

  
  const [div1ContentState, setDiv1ContentState] = useState({ type: null, data: null });
  const [div2ContentState, setDiv2ContentState] = useState({ type: null, data: null });

  const onChange = (text) => setTexto(text);
  const onData = (json) => setCurrentResponseData(json);
  const onClick = (name) => setLastActionType(name);

  useEffect(() => {
    
    if (currentResponseData && currentResponseData.listo && lastActionType !== "") {
      const newDataType = lastActionType; 
      const newData = currentResponseData;
    
      if (div1ContentState.type === newDataType) {
        setDiv1ContentState({ type: newDataType, data: newData });
      }
      
      else if (div2ContentState.type === newDataType) {
        setDiv2ContentState({ type: newDataType, data: newData });
      }
      
      else {
        if (!div1ContentState.type) {
          setDiv1ContentState({ type: newDataType, data: newData });

        }
        
        else if (!div2ContentState.type) {
          setDiv2ContentState({ type: newDataType, data: newData });

        }

        
      }
      setCurrentResponseData(null);
      setLastActionType("");
    }
  }, [currentResponseData, lastActionType, div1ContentState, div2ContentState]);

  return (
    <div className="padre">
      <TextAreaComponent setT={onChange} />
      <section className="hijo">
        <div className="botones">
          <BotonComponent
            id="botonT"
            btnName="Tokenizar"
            texto={texto}
            sData={onData}
            sBoton={onClick}
            url="http://localhost:8081/Paginacion?size=10&page=0"
          />
          <BotonComponent
            id="botonC"
            btnName="Compilar"
            texto={texto}
            sData={onData}
            sBoton={onClick}
            url="http://localhost:8081/Compilador"
          />
        </div>
        <div className='hijoD'>
          <div className="contenedor">
            {div1ContentState.type === "Tokenizar" && div1ContentState.data?.listo && (
              <TokensComponent json={div1ContentState.data} texto={texto} />
            )}
            {div1ContentState.type === "Compilar" && div1ContentState.data?.listo && (
              <CompiComponent json={div1ContentState.data} />
            )}
          </div>
          <div className="contenedor2">
            {div2ContentState.type === "Tokenizar" && div2ContentState.data?.listo && (
              <TokensComponent json={div2ContentState.data} texto={texto} />
            )}
            {div2ContentState.type === "Compilar" && div2ContentState.data?.listo && (
              <CompiComponent json={div2ContentState.data} />
            )}
          </div>
        </div>
      </section>
    </div>
  );
};