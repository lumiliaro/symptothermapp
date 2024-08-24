import { FieldValues, UseFormReturn } from "react-hook-form";

export default function handleFormErrors<T extends FieldValues>(
    form: UseFormReturn<T, unknown, undefined>,
    errorResponse: any
) {
    if (
        errorResponse?.status === 400 &&
        errorResponse?.data?.errors instanceof Array
    ) {
        errorResponse.data.errors.forEach((error: any) => {
            form.setError(error.field, {
                message: error.defaultMessage,
                type: "value",
            });
        });
    }
}
