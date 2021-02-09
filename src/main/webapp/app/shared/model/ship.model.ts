import { ShipType } from 'app/shared/model/enumerations/ship-type.model';

export interface IShip {
  id?: number;
  name?: string;
  vesselType?: ShipType;
}

export class Ship implements IShip {
  constructor(public id?: number, public name?: string, public vesselType?: ShipType) {}
}
