import { ISensorReadings } from 'app/shared/model/sensor-readings.model';

export interface IContainer {
  id?: number;
  description?: string;
  senderName?: string;
  senderId?: number;
  receiverName?: string;
  receiverId?: number;
  readings?: ISensorReadings[];
  shipmentId?: number;
}

export class Container implements IContainer {
  constructor(
    public id?: number,
    public description?: string,
    public senderName?: string,
    public senderId?: number,
    public receiverName?: string,
    public receiverId?: number,
    public readings?: ISensorReadings[],
    public shipmentId?: number
  ) {}
}
