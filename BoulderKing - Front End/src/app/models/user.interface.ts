import { Role } from "../components/user/role.enum"
import { TipoUser } from "../components/user/tipo-user.enum"

export interface User {
    "id": string,
            "email": string,
            "immagineProfilo"?:{
                "id"?: number,
                "name"?: string,
                "type"?: string,
                "image"?: string
            },
            "foto"?: string,
            "name"?: string,
            "surname"?: string,
            "posizioneClassifica"?: number,
            "puntiClassifica"?: number,
            "listaEventi"?: any,
            "password"?: string,
            "role"?: Role,
            "tipoUser"?: TipoUser,
            "dataRegistrazione"?: string,
            "nomeEnte"?: string,
            "numeroTelefonico"?: string,
            "emailContatto"?: string,
            "indirizzo"?: string,
            "informazioni"?: string,
            "tipoEnte"?: string,
            "listaEventiOrganizzati"?: any,
            "username"?: string,
            "primoPosto"?: number,
            "numeroPodi"?: number,
            "numeroPartecipazioni"?: number,
            "zonaItalia"?: string,
            "immagineCopertina"?: string,
            "orari"?: string,
            "longitudine"?:number,
            "latitudine"?: number,
            "regione"?: string,
            "provincia"?: string,
            "citta"?: string,
            "sito"?: string
}
