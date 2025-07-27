import React, { useEffect, useState } from 'react';
import { useFetch } from '../Hooks/useFetch';

export const TokensComponent = ({json,texto}) => {
    
    const [data, setData] = useState(json);
    const [pagina, setPagina] = useState(0);
    
    const [totalP, setTotalP] = useState(json?.json?.Tokens?.page?.totalPages || 0);
    const [shouldFetch, setShouldFetch] = useState(false); 

    
    const url = `http://localhost:8081/Paginacion?size=10&page=${pagina}`;

    
    const fetchData = useFetch(url, texto, shouldFetch);

    useEffect(() => {
        if (fetchData.listo) {
            setData(fetchData); 
            setTotalP(fetchData.json.Tokens.page.totalPages);
            setShouldFetch(false); 
        }
    }, [fetchData]); 

    useEffect(() => {
        if (json?.listo) {
            setData(json);
            setTotalP(json.json.Tokens.page.totalPages);
            setPagina(json.json.Tokens.page.number); 
        }
    }, [json]); 

    const onClick = (estado) => {
        if (estado === "suma") {
            setPagina(prevPagina => prevPagina + 1);
        } else if (estado === "resta") {
            setPagina(prevPagina => prevPagina - 1);
        }
        setShouldFetch(true); 
    };

    return (
        <>
            {data?.listo && (
                <>
                    <table className='table'>
                        <thead>
                            <tr>
                                <td>Token</td>
                                <td>Lexema</td>
                            </tr>
                        </thead>
                        <tbody>
                            {data.json.Tokens.content.map((value, index) => (
                                <tr key={index}> 
                                    <td>{value.token}</td>
                                    <td>{value.lexema}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <button
                        className="btn btn-outline-success"
                        onClick={() => onClick("resta")}
                        disabled={pagina === 0}
                    >{"<"}</button>
                    <button
                        className="btn btn-outline-success"
                        onClick={() => onClick("suma")}
                        disabled={pagina+1 == totalP} 
                    >{">"}</button>
                    <span style={{ marginLeft: '10px', marginRight: '10px' }}>
                        Página {pagina +1} de {totalP}
                    </span>
                </>
            )}
        </>
    );
};