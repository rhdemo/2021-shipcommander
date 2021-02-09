import { IContainer } from 'app/shared/model/container.model';

export interface IShipment {
  id?: number;
  startPortName?: string;
  startPortId?: number;
  endPortName?: string;
  endPortId?: number;
  shipName?: string;
  shipId?: number;
  containers?: IContainer[];
}

export class Shipment implements IShipment {
  constructor(
    public id?: number,
    public startPortName?: string,
    public startPortId?: number,
    public endPortName?: string,
    public endPortId?: number,
    public shipName?: string,
    public shipId?: number,
    public containers?: IContainer[]
  ) {}
}
