export interface IPort {
  id?: number;
  name?: string;
  latitude?: number;
  longtitude?: number;
}

export class Port implements IPort {
  constructor(public id?: number, public name?: string, public latitude?: number, public longtitude?: number) {}
}
