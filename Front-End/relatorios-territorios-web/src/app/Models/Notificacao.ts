export interface Notificacao {
  id: number;
  message: string;
  topic:string;
  idPublicadorEmissor:string;
  idPublicadorRemetente:string;
  createdAt:Date;
  lida:boolean;
}