export interface ISensorReadings {
  id?: number;
  temperature?: number;
  latitude?: number;
  longtitude?: number;
  humidityRate?: number;
  containerId?: number;
}

export class SensorReadings implements ISensorReadings {
  constructor(
    public id?: number,
    public temperature?: number,
    public latitude?: number,
    public longtitude?: number,
    public humidityRate?: number,
    public containerId?: number
  ) {}
}
