export interface ICompany {
  id?: number;
  name?: string;
  addressLine?: string;
  city?: string;
  country?: string;
}

export class Company implements ICompany {
  constructor(public id?: number, public name?: string, public addressLine?: string, public city?: string, public country?: string) {}
}
