import { User } from "./user.interface";

export interface Evento {
    "id": string,
    "nomeEvento"?: string,
    "localita"?: string,
    "organizzatore"?: User,
    "creatoreEvento"?: string,
    "puntiEvento"?: number,
    "data"?: string,
    "classifica"?: any,
    "immagineEvento"?: string,
    "citta"?: string,
    "provincia"?: string,
    "regione"?: string,
    "isPassed"?: string,
    "sito"?: string,
    "info"?: string,
    "zonaItalia"?: string
}
