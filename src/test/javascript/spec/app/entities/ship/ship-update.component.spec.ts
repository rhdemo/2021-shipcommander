import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { ShipUpdateComponent } from 'app/entities/ship/ship-update.component';
import { ShipService } from 'app/entities/ship/ship.service';
import { Ship } from 'app/shared/model/ship.model';

describe('Component Tests', () => {
  describe('Ship Management Update Component', () => {
    let comp: ShipUpdateComponent;
    let fixture: ComponentFixture<ShipUpdateComponent>;
    let service: ShipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [ShipUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ship(123);
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
        const entity = new Ship();
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
