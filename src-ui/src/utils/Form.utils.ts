import { FormState } from "react-hook-form";

export const isFormDirty = (formState: FormState<any>) => {
  return Object.keys(formState.dirtyFields).length === 0;
}