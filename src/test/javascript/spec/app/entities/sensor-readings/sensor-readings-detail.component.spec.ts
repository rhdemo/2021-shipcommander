import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { SensorReadingsDetailComponent } from 'app/entities/sensor-readings/sensor-readings-detail.component';
import { SensorReadings } from 'app/shared/model/sensor-readings.model';

describe('Component Tests', () => {
  describe('SensorReadings Management Detail Component', () => {
    let comp: SensorReadingsDetailComponent;
    let fixture: ComponentFixture<SensorReadingsDetailComponent>;
    const route = ({ data: of({ sensorReadings: new SensorReadings(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [SensorReadingsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SensorReadingsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SensorReadingsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sensorReadings on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sensorReadings).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
