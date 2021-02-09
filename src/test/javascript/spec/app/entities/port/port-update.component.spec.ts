import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { PortUpdateComponent } from 'app/entities/port/port-update.component';
import { PortService } from 'app/entities/port/port.service';
import { Port } from 'app/shared/model/port.model';

describe('Component Tests', () => {
  describe('Port Management Update Component', () => {
    let comp: PortUpdateComponent;
    let fixture: ComponentFixture<PortUpdateComponent>;
    let service: PortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [PortUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PortUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PortUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PortService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Port(123);
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
        const entity = new Port();
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
