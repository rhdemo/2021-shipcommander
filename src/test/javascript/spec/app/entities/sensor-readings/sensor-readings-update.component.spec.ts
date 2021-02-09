import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { SensorReadingsUpdateComponent } from 'app/entities/sensor-readings/sensor-readings-update.component';
import { SensorReadingsService } from 'app/entities/sensor-readings/sensor-readings.service';
import { SensorReadings } from 'app/shared/model/sensor-readings.model';

describe('Component Tests', () => {
  describe('SensorReadings Management Update Component', () => {
    let comp: SensorReadingsUpdateComponent;
    let fixture: ComponentFixture<SensorReadingsUpdateComponent>;
    let service: SensorReadingsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [SensorReadingsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SensorReadingsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SensorReadingsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SensorReadingsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SensorReadings(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SensorReadings();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
